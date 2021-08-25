export interface UserProfileModel {
  id?: number;
  email?: string;
  firstName?: string;
  lastName?: string;
  emailAddress?: string;
  phoneNumbers? :string[];
  cnp?: string;
  registrationNumber?: string;
  secretaryName?: string;
  secretaryPhoneNumbers?: string[];
  secretaryEmailAddress?: string;
}
