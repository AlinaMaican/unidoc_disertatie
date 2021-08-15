package ro.alina.unidoc.mapper;

import ro.alina.unidoc.entity.SecretaryDocument;
import ro.alina.unidoc.model.SecretaryDocumentModel;

public class SecretaryDocumentMapper {

    public static SecretaryDocumentModel toSecretaryDocumentModel(final SecretaryDocument secretaryDocument){
        return SecretaryDocumentModel.builder()
                .id(secretaryDocument.getId())
                .description(secretaryDocument.getDescription())
                .name(secretaryDocument.getName())
                .filePathName(secretaryDocument.getFilePathName())
                .endDateOfUpload(secretaryDocument.getEndDateOfUpload())
                .build();
    }
}
