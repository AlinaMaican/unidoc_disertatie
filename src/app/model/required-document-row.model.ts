import {StudentDocumentModel} from "./student-document.model";

export interface RequiredDocumentRowModel{
  secretaryDocumentId: number;
  secretaryDocumentName: string;
  secretaryDocumentPathName: string;
  endDateOfUpload: string;
  studentDocumentModel: StudentDocumentModel;
}
