import { instance } from "./index";

function enterChat(boardId) {
  instance.post(`/board/chat/enter/${boardId}`);
}

export { enterChat };
