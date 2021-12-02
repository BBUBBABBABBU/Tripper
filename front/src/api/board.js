import { instance } from "./index";

function createPost(postData) {
  return instance.post("/board/create", postData);
}
function deletePost(postId) {
  return instance.delete(`/board/post/${postId}/delete`);
}
function infoPost(postId) {
  return instance.get(`/board/post/${postId}`);
}
function editPost(postData) {
  return instance.post(`/board/post/${postData.postId}/update`, postData);
}
function fetchBoardList() {
  const token = localStorage.getItem("jwtToken");
  console.log(token);
  instance.defaults.headers.common["Authorization"] = token;

  return instance.get("/board/list");
}
function addLikes(boardId) {
  return instance.get(`/board/post/${boardId}/addLikes`);
}
function subLikes(boardId) {
  return instance.get(`/board/post/${boardId}/subtrackLikes`);
}
export {
  createPost,
  deletePost,
  editPost,
  fetchBoardList,
  infoPost,
  addLikes,
  subLikes,
};
