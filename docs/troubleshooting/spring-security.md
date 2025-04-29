# Spring Security Login/Signup 

## 1️⃣ React axios 403 error

### ⚠️ Error Log

**client f12 console.error**
```
{
    "message": "Request failed with status code 403",
    "name": "AxiosError",
    "stack": "AxiosError: Request failed with status code 403\n    at settle (http://localhost:3000/static/js/bundle.js:105186:12)\n    at XMLHttpRequest.onloadend (http://localhost:3000/static/js/bundle.js:103813:66)\n    at Axios.request (http://localhost:3000/static/js/bundle.js:104312:41)\n    at async onSubmit (http://localhost:3000/main.d6057714c0d8780f45aa.hot-update.js:124:19)\n    at async http://localhost:3000/static/js/bundle.js:109692:9",
    "config": {
        ...
        "headers": {
            "Accept": "application/json, text/plain, */*",
            "Content-Type": "application/json"
        },
        
        ...
    },
    "code": "ERR_BAD_REQUEST",
    "status": 403
}
```

**spring boot server error**
```
Invalid CSRF token found for http://localhost:8080/auth/signup
```

### 🧾 Cause

`F12 -> Application -> Cookies -> http://localhost:8080/auth/signup` -> missing `XSRF-TOKEN`

<br>

### ⚡ Solution

I couldn't figure correct method out,

I set `csrf.disabled()` and changed order between cors and csrf

```
//SecurityConfig.java

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable())
        ...

}

```


## 2️⃣ Incorrect Redirect Google OAuth after Standard Login

### ⚠️ Error Log

When I logined without google auth, I encountered this error. 

```

Uncaught (in promise) TypeError: Failed to fetch

GET https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=8231…https://planmytodos-api-production.up.railway.app/login/oauth2/code/google net::ERR_FAILED 302 (Found)

Access to fetch at 'https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=823102371522-7m7pi6vadhlj0hg8g66r1bdk8pjuhnvb.apps.googleusercontent.com&scope=email%20profile&state=CS-SDyHuC8vgkZbc4UfbOuwg4_lsxGD2lM0wAEvRmZk%3D&redirect_uri=https://planmytodos-api-production.up.railway.app/login/oauth2/code/google' (redirected from 'https://planmytodos-api-production.up.railway.app/todo/fetchTodosByDate') from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
```

### 🧾 Cause

After Login, I requested method get user session (`fetchSession`)
<br> But I missed to configure Spring Security's authentication settings. (I only configured user object's session)

**My source**
```
@Transactional
public UserResponseDTO login(LoginRequestDTO dto) {
    
    ...
    
    httpSession.setAttribute("user", new SessionUser(user));
    
    ...
}
```

### ⚡ Solution

notify to Spring Security authentication user info

```
UserDetails userDetails = new CustomUserDetails(user);
		
SecurityContext context = SecurityContextHolder.createEmptyContext();
Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, dto.getPassword(), userDetails.getAuthorities());
context.setAuthentication(authentication);

SecurityContextHolder.setContext(context);

```