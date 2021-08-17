import {SecretaryDocumentModel} from "./secretary-document.model";

export interface SecretaryDocumentTableDataModel{
  allocationId: number;
  allocationName: string;
  tableData: SecretaryDocumentModel[]
}
