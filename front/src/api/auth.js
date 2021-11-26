import { instance } from "./index.js";

function loginUser(form) {
  console.log(form);
  return instance.post(`/login`, form);
}
function signupUser(userData) {
  console.log(userData);
  const response = instance.post("/user", userData);
  return response.code;
}
function socialSignupUser(userData) {
  console.log(userData);
  return instance.post("/user/socicalSignup", userData);
}
function certUserEmail(Email) {
  return instance.post("/user/certEmail", Email);
}
function userIdCheck(userId) {
  return instance.get("/user/checkExists", userId);
}

export { loginUser, signupUser, certUserEmail, userIdCheck, socialSignupUser };
