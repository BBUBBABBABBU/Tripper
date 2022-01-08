import { ApiService } from "./service";

function tripList(name) {
  return ApiService.get("/trips", name);
}

function createTrip(tripData, name) {
  return ApiService.postMemId("/trips", tripData, name);
}

function deleteTrip(tripId) {
  return ApiService.delete("/trips", tripId);
}

export { tripList, createTrip, deleteTrip };
