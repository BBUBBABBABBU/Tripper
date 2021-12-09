function formatDateTime(value) {
  const date = new Date(value);
  const year = date.getFullYear();
  let month = date.getMonth();
  month = month > 9 ? month : `0${month}`;
  const day = date.getDate();
  let hours = date.getHours();
  hours = hours > 9 ? hours : `0${hours}`;
  const minutes = date.getMinutes();
  return `${year}-${month}-${day} ${hours}:${minutes}`;
}
function formatDate(value) {
  const date = new Date(value);
  const year = date.getFullYear();
  let month = date.getMonth();
  month = month > 9 ? month : `0${month}`;
  const day = date.getDate();
  return `${year}-${month}-${day}`;
}

export { formatDate, formatDateTime };
