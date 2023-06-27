import {CRIENT_HOST} from "./config/HostConfig";

const CLIENT_ID = "d16ee03edff29a44b1760307dcd1d15d";
const REDIRECT_URI = "http://" + CRIENT_HOST + "/oauth/callback/kakao";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;