# Google OAuth API

## 1️⃣ Access blocked

I encountered this error when I clicked Google login button for test in spring boot server

### ⚠️ Error Log

```
Access blocked: This app’s request is invalid
```

### 🧾 Cause

Google Cloud Console settings is invalid.

### ⚡ Solution

1. Check Google Cloud Console settings
    - Spring Boot Server Redirection URL (승인된 리디렉션 URI)