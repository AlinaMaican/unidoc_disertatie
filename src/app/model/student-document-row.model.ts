import {StudentModel} from "./student.model";

export interface StudentDocumentRowModel{
  studentModel: StudentModel;
  studyGroup: string;
  studyGroupId: number;
  fileName: string;
  filePath: string;
  dateOfUpload: string;
  status: string
}
