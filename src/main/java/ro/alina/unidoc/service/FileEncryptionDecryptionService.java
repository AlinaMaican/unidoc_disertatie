package ro.alina.unidoc.service;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileEncryptionDecryptionService {

    public ByteArrayOutputStream decryptFile(final String fileName, final String password) throws IOException {
        File file = new File(fileName);
        // Load the PDF file
        PDDocument pdd = null;
        try {
            pdd = PDDocument.load(file, password);
            pdd.setAllSecurityToBeRemoved(true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdd.save(byteArrayOutputStream);
            pdd.close();
            return byteArrayOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void encryptFile(final String fileName, final String password) throws IOException {
        File f = new File(fileName);
        PDDocument pdd = PDDocument.load(f);

        // step 2.Creating instance of AccessPermission
        // class
        AccessPermission ap = new AccessPermission();

        // step 3. Creating instance of
        // StandardProtectionPolicy
        StandardProtectionPolicy stpp
                = new StandardProtectionPolicy(password, password, ap);

        // step 4. Setting the length of Encryption key
        stpp.setEncryptionKeyLength(128);

        // step 5. Setting the permission
        stpp.setPermissions(ap);

        // step 6. Protecting the PDF file
        pdd.protect(stpp);

        // step 7. Saving and closing the the PDF Document
        pdd.save(fileName);
        pdd.close();

        System.out.println("PDF Encrypted successfully...");
    }
}
