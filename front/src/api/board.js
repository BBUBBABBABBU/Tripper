import { instance } from "./index";

function createPost(postData) {
  return instance.post("/board/create", postData);
}
function deletePost(postId) {
  return instance.get(`/board/post/${postId}/delete`);
}
function infoPost(postId) {
  return instance.get(`/board/post/${postId}`);
}
function editPost(postData) {
  return instance.post(`/board/post/${postData.id}/update`, postData);
}
async function fetchBoardList() {
  const response = await instance.get("/board/list");
  console.log(response);
  return response;
}
function addLikes(instanceId) {
  return instance.get(`/board/post/${instanceId}/addLikes`);
}
function subLikes(instanceId) {
  return instance.get(`/board/post/${instanceId}/subtractLikes`);
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
