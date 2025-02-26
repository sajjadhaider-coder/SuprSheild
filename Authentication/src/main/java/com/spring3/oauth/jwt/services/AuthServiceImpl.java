package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.dtos.UserInfoResponse;
import com.spring3.oauth.jwt.models.UserInfo;
import com.spring3.oauth.jwt.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.text.StrTokenizer;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    public static final String DYNAMODB_LOCATION_API = "http://ip-api.com/json/";
    ModelMapper modelMapper = new ModelMapper();
    public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");

    @Override
    public UserInfo saveUser(UserInfo user) {
        try {
            if (user.getUsername() == null) {
                throw new RuntimeException("Parameter account number is not found in request..!!");
            } else if (user.getPassword() == null) {
                throw new RuntimeException("Parameter password is not found in request..!!");
            }
            Optional<UserInfo> persitedUser = Optional.of(new UserInfo());
            UserInfo savedUser = null;

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = user.getPassword();
            String encodedPassword = encoder.encode(rawPassword);

            // UserInfo user = modelMapper.map(userInfoRequest, UserInfo.class);
            user.setUsername(user.getUsername());
            user.setPassword(encodedPassword);
            user.setStatus("Active");
            if (user.getId() > 0) {
                UserInfo oldUser = userRepository.findFirstById(user.getId());
                oldUser.setCreatedBy(String.valueOf(oldUser.getId()));
                if (oldUser != null) {
                    oldUser.setId(user.getId());
                    oldUser.setPassword(user.getPassword());
                    oldUser.setUsername(user.getUsername());
                    //  oldUser.setVerificationCode(user.getVerificationCode());
                    oldUser.setUpdatedAt(LocalDateTime.now());
                    oldUser.setUpdatedBy(String.valueOf(oldUser.getId()));
                    oldUser.setRoles(user.getRoles());

                    savedUser = userRepository.save(oldUser);
                    persitedUser = userRepository.findById(savedUser.getId());
                } else {
                    throw new RuntimeException("Can't find record with identifier: " + persitedUser.get().getId());
                }
            } else {
//            user.setCreatedBy(currentUser);
                user.setCreatedAt(LocalDateTime.now());
                persitedUser = Optional.of(userRepository.save(user));
            }
            return persitedUser.get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserInfo getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetail = (UserDetails) authentication.getPrincipal();
            String usernameFromAccessToken = userDetail.getUsername();
            UserInfo user = userRepository.findByUsername(usernameFromAccessToken);
            // UserInfoResponse userResponse = modelMapper.map(user, UserInfoResponse.class);

            if (user.getUsername() != null)
                user.setUsername(user.getUsername());

            return user;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<UserInfo> getAllUser() {
        try {
            List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
            Type setOfDTOsType = new TypeToken<List<UserInfoResponse>>() {
            }.getType();
            List<UserInfoResponse> userResponses = modelMapper.map(users, setOfDTOsType);
            for (int i = 0; i < users.size(); i++) {
                userResponses.get(i).setUsername(users.get(i).getUsername());
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String returnClientIp(HttpServletRequest request)
    {
        boolean found = false;
        String IPAddress;
        if ((IPAddress = request.getHeader("x-forwarded-for")) != null) {
            StrTokenizer tokenizer = new StrTokenizer(IPAddress, ",");
            while (tokenizer.hasNext())
            {
                IPAddress = tokenizer.nextToken().trim();
                if (isIPv4Valid(IPAddress) && !isIPv4Private(IPAddress)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {IPAddress = request.getRemoteAddr();}
        return IPAddress;
    }

    @Override
    public UserInfo updateUser(UserInfo userInfo, HttpServletRequest httpServletRequest) {
        try {
            Optional<UserInfo> checkUser = null;
            UserInfo userResponse = null;
            if (userInfo.getId() > 0) {
                checkUser = userRepository.findById(userInfo.getId());
            }
            if (checkUser != null) {
                checkUser.get().setUpdatedAt(LocalDateTime.now());
                checkUser.get().setUpdatedBy(userInfo.getUpdatedBy());
                //  checkUser.setUserId(userInfo.getId());
                checkUser.get().setIpAddress(userInfo.getIpAddress());
                checkUser.get().setUserLocation(this.getIPLocation(userInfo.getUserLocation()));
                checkUser.get().setUpdatedBy(String.valueOf(userInfo.getId()));
                checkUser.get().setDeviceType(userInfo.getDeviceType());
                checkUser = Optional.of(userRepository.save(checkUser.get()));
            } else {
                userInfo = userRepository.save(userInfo);
            }
            return userResponse;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Boolean deleteUser(Long userId) {
        Boolean isDeleted = false;
        try {
            Optional<UserInfo> userInfo = userRepository.findById(userId);
            if(!userInfo.isEmpty()) {
                userRepository.delete(userInfo.get());
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return isDeleted;
    }

    @Override
    public UserInfo getUserByUserName(String userName) {
        UserInfo userInfo = userRepository.findByUsername(userName);
        //UserInfo user = modelMapper.map(userInfo, UserInfoResponse.class);
        return userInfo;
    }

    @Override
    public UserInfo findByUserId(String userId) {
        return null;
    }

    public String getIPLocation(String ip) {
        String apiLocation = null;
        String inputLine = "";

        try {
            String apiUrl = DYNAMODB_LOCATION_API + ip;
            URL obj = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            JSONObject json = new JSONObject(response.toString());
            if ( json == null || json.get("status").equals("fail"))
                apiLocation = "NA";
            else
                apiLocation = json.get("city") + ", " + json.get("country");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return apiLocation;
    }
    private static boolean isIPv4Valid(String ip) {
        return pattern.matcher(ip).matches();
    }
    public static boolean isIPv4Private(String ip) {
        long longIp = ipV4ToLong(ip);
        return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255")) ||
                (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255")) ||
                longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
    }
    public static long ipV4ToLong(String ip) {
        String[] octets = ip.split("\\.");
        return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16) +
                (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }
}
