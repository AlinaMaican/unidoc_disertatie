import {StudyDetailsModel} from "./study-details.model";

export interface SecretaryListModel{
  id: number;
  firstName: string;
  lastName: string;
  emailAddress: string;
  phoneNumbers: string[];
  studyDetails: StudyDetailsModel[];

}
