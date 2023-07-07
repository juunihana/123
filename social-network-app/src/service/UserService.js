import axios from "axios";

class UserService {
  constructor() {
    this.restService = axios.create({
      baseURL: 'http://localhost:8087/api/v1',
      headers: {
        'Content-Type': 'application/json'
      }
    })
  }

  get(username) {
    return this.restService.get(`/user/${username}`)
  }
}

export default new UserService()