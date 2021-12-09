export default {
  SET_TOKEN(state, token) {
    state.token = token;
  },
  SET_USER_LOGIN(state, memId) {
    state.loginSuccess = true;
    state.memId = memId;
  },
};
