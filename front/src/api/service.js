import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";
import JwtService from "./jwt.service";

/**
 * Service to call HTTP request via Axios
 */
const ApiService = {
  init() {
    Vue.use(VueAxios, axios);
    // 아래 경로가 API 서버의 주소 : 로컬 테스트 시에는 포트번호를 수정하여 사용한다.
    Vue.axios.defaults.baseURL = "http://localhost:8080";
  },

  /**
   * Set the default HTTP request headers
   */
  setHeader() {
    Vue.axios.defaults.headers.common[
      "Authorization"
    ] = `Token ${JwtService.getToken()}`;
  },

  query(resource, params) {
    return Vue.axios.get(resource, params).catch((error) => {
      // console.log(error);
      throw new Error(`${error}`);
    });
  },

  /**
   * Send the GET HTTP request
   * @param resource
   * @param slug
   * @returns {*}
   */
  get(resource, slug = "") {
    return Vue.axios.get(`${resource}/${slug}`).catch((error) => {
      // console.log(error);
      throw new Error(`${error}`);
    });
  },

  /**
   * Set the POST HTTP request
   * @param resource
   * @param params
   * @returns {*}
   */
  post(resource, params) {
    console.log(params);
    return Vue.axios.post(`${resource}`, params);
  },

  postMemId(resource, params, memId) {
    return Vue.axios.post(`${resource}`, params, memId);
  },

  /**
   * Send the UPDATE HTTP request
   * @param resource
   * @param slug
   * @param params
   * @returns {IDBRequest<IDBValidKey> | Promise<void>}
   */
  update(resource, slug, params) {
    return Vue.axios.put(`${resource}/${slug}`, params);
  },

  /**
   * Send the PUT HTTP request
   * @param resource
   * @param params
   * @returns {IDBRequest<IDBValidKey> | Promise<void>}
   */
  put(resource, params) {
    return Vue.axios.put(`${resource}`, params);
  },

  /**
   * Send the DELETE HTTP request
   *
   * @param resource
   * @returns {*}
   */
  delete(resource) {
    return Vue.axios.delete(resource).catch((error) => {
      // console.log(error);
      throw new Error(`${error}`);
    });
  },
};

export default ApiService;
