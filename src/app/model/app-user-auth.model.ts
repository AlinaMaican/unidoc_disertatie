import {RoleType} from "../type/role.type";

export interface AppUserAuthModel {
  active: boolean;
  email: string;
  role: RoleType;
}
