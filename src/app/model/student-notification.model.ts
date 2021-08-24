
export interface StudentNotificationModel{
  id: number;
  documentName: string;
  message: string;
  date: string;
  studentDocumentId: number;
  documentType: string;
  seen: boolean;
}
