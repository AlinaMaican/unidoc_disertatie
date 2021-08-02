import {StudyDetailsModel} from "./study-details.model";

export interface SecretaryListModel{
  firstName: string;
  lastName: string;
  emailAddress: string;
  phoneNumbers: string[];
  studyDetails: StudyDetailsModel[];

}
