## Spring security with JWT Token

--- 

### API Spec

#### Sign up

```http request
POST /api/auth/sign-up

{
  "userId": "martin",
  "password": "12345678"
}
```

Response information

```json
{
    "code": "SUCCESS",
    "content": null,
    "message": "",
    "success": true
}
```

#### Sign in

```http request
POST /api/auth/sign-in

{
  "userId": "martin",
  "password": "12345678"
}
```

Response information

```json
{
    "code": "SUCCESS",
    "content": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ0aW4iLCJyb2xlIjoiUk9MRV9VU0VSIiwidXNlciI6eyJ1c2VySWQiOiJtYXJ0aW4iLCJwZXJtaXNzaW9uIjoiUk9MRV9VU0VSIn19.DBzD86P7AZHNRels3Cv_wyRNvMpR3s-4DxjRuEBG2Q4",
    "message": "",
    "success": true
}
```

### Reference
- [https://www.javainuse.com/spring/boot-jwt](https://www.javainuse.com/spring/boot-jwt)
- [https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/](https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/)