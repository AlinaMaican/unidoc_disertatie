import {RoleType} from "../type/role.type";

export interface AppUserAuthModel {
  id: number;
  active: boolean;
  email: string;
  role: RoleType;
}
