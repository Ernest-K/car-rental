import { User } from "@/types/types";

export default function useAuth() {
  const isLogged = () => {
    return localStorage.getItem("user") !== null;
  };

  const login = (user: User) => {
    localStorage.setItem("user", btoa(`${user.username}:${user.password}`));
  };

  const logout = () => {
    localStorage.removeItem("user");
  };

  const getUser = () => {
    return localStorage.getItem("user");
  };

  return { isLogged, login, logout, getUser };
}
