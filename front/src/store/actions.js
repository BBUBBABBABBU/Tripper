export default {
  setToken(context, token) {
    context.commit("SET_TOKEN", token);
  },
  loginSuccess(context, memId) {
    context.commit("SET_USER_LOGIN", memId);
  },
};
