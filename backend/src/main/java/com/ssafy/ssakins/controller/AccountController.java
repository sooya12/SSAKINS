package com.ssafy.ssakins.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.ssakins.dto.AccountInfo;
import com.ssafy.ssakins.entity.Account;
import com.ssafy.ssakins.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
        this.kakaoRedirectBackURI = BACK_SERVER_URI + "/account/login";
        this.kakaoRedirectFrontURI = FRONT_SERVER_URI + "/main";
    }

//    @Value("http://localhost:8000")
//    private String FRONT_SERVER_URI;
//    @Value("http://localhost:8080")
    @Value("${front.url}")
    private String FRONT_SERVER_URI;
    @Value("${backend.url}")
    private String BACK_SERVER_URI;

    @Value("${backend.url}/account/login")
    private final String kakaoRedirectBackURI;
    @Value("${front.url}/login/")
    private final String kakaoRedirectFrontURI;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code"); // authorize_code
        String access_token = getKakaoAccessToken(code); // 토큰 얻어오기

        // 이메일 정보 얻어오기
        AccountInfo accountInfo = getKakaoUserInfo(access_token);

        String token = "";

        Optional<Account> accountOp = accountRepository.findByEmail(accountInfo.getUserEmail());

        if(accountOp.isPresent()){ // 이미 정보가 있는 회원
            String imageUrl;
            if(accountOp.get().getImage().isEmpty()) {
                imageUrl = "none";
            } else {
                imageUrl = accountOp.get().getImage();
            }
            response.sendRedirect(kakaoRedirectFrontURI + accountOp.get().getEmail() + "/" + URLEncoder.encode(accountOp.get().getName(), "UTF-8") + "/" + URLEncoder.encode(imageUrl, "UTF-8"));
            return ResponseEntity.ok().body("기존 회원");
        } else {
            Account account;
            account = Account.builder()
                    .email(accountInfo.getUserEmail())
                    .name(accountInfo.getNickname())
                    .image(accountInfo.getImageUrl())
                    .build();
            accountRepository.save(account);
            response.sendRedirect(kakaoRedirectFrontURI + account.getEmail() + "/" + URLEncoder.encode(account.getName(), "UTF-8") + "/" + URLEncoder.encode(account.getImage(), "UTF-8"));
            return ResponseEntity.ok().body("새로운 회원 생성");
        }
    }

    // 로그인 토큰 가져오는 함수
    private String getKakaoAccessToken(String authorize_code) {
        String access_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            //con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // POST 요청을 위한 설정
            con.setRequestMethod("POST");
            con.setDoOutput(true); // 쓰는 기능 on
            System.out.println(authorize_code);
            // POST 요청에 필요한 파라미터를 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=5087dfcc2c61ed08e9cf4a4282fbbf2c");
            sb.append("&redirect_uri=" + kakaoRedirectBackURI); // 로그인 함수로 리다이렉트 하게 해 줌
//            sb.append("&redirect_uri=" + "http://localhost:8080/account/login"); // 로그인 함수로 리다이렉트 하게 해 줌
            sb.append("&code=" + authorize_code);
            sb.append("&client_secret=uJMkJL2dlOuueP3xHbnj00XDgccRH4q9");

            bw.write(sb.toString());
            bw.flush();

            int responseCode = con.getResponseCode(); // 성공 : 200
            System.out.println(BACK_SERVER_URI);
            System.out.println(kakaoRedirectBackURI);
            System.out.println(responseCode);
            // 요청을 통해 얻은 json 형식 response 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            String result = "";

            while((line=br.readLine()) != null) {
                result += line;
            }

            // gson 라이브러리의 클래스로 json 파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    // 토큰으로 로그인 정보(이메일) 가져오는 함수
    private AccountInfo getKakaoUserInfo(String access_token) {

        AccountInfo accountInfo = new AccountInfo();

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            // 요청에 필요한 header에 포함될 내용
            con.setRequestProperty("Authorization", "Bearer "+access_token);

            int responseCode = con.getResponseCode(); // 성공 : 200

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            System.out.println(element);

            accountInfo.setUserEmail(element.getAsJsonObject().get("kakao_account").getAsJsonObject().getAsJsonObject().get("email").getAsString());
            accountInfo.setNickname(element.getAsJsonObject().get("properties").getAsJsonObject().getAsJsonObject().get("nickname").getAsString());

            String imageUrl;
            JsonElement imageJson = element.getAsJsonObject().get("properties").getAsJsonObject().getAsJsonObject().get("thumbnail_image");
            imageUrl = imageJson.isJsonNull() ? "none" : imageJson.getAsString();
            accountInfo.setImageUrl(imageUrl);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return accountInfo;
    }

}
