import {StudentModel} from "./student.model";

export interface StudentDocumentRowModel{
  documentId: number;
  studentModel: StudentModel;
  studyGroup: string;
  studyGroupId: number;
  name: string;
  filePath: string;
  dateOfUpload: string;
  status: string
}
