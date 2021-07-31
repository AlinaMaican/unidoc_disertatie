--PASSWORD IS PASSWORD AND IS HASHED
INSERT INTO users VALUES(nextval('users_seq'), 'ADMIN', 'alina.maican@my.fmi.unibuc.ro', '$2y$12$jbkinGYV9wduwiF4HFHOI.D/Vu6PH.tMPEiaVsYZVbLrIMv7Rou8C', true);

INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 'LICENTA');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 'MASTER');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 'DOCTORAT');

--LICENTA
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'INFORMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'CALCULATOARE SI TEHNOLOGIA INFORMATIEI');

--MASTER
INSERT INTO domain VALUES(nextval('domain_seq'), 2, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 2, 'INFORMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 2, 'BIOSTATISTICA');

--DOCTORAT
INSERT INTO domain VALUES(nextval('domain_seq'), 3, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 3, 'INFORMATICA');

-------------------------------------------------------------------------------------------------------------------
--LICENTA DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA APLICATA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA INFORMATICA');

--LICENTA DOMENIUL INFO
INSERT INTO study_program VALUES(nextval('study_program_seq'), 2, 'INFORMATICA');

--LICENTA DOMENIUL CTI
INSERT INTO study_program VALUES(nextval('study_program_seq'), 3, 'CALCULATOARE SI TEHNOLOGIA INFORMATIEI');

--MASTER DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 4, 'STUDII AVANSATE IN MATEMATICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 4, 'MATEMATICA DIDACTICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 4, 'PROBABILITATI SI STATISTICA');

--MASTER DOMENIUL INFORMATICA
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'BAZE DE DATE SI TEHNOLOGII SOFTWARE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'INTELIGENTA ARTIFICIALA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'INGINERIE SOFTWARE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'SECURITATE SI LOGICA APLICATA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'SISTEME DISTRIBUITE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'DATA SCIENCE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'PROCESAREA LIMBAJULUI NATURAL');

--MASTER DOMENIUL BIOSTATISTICA
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'BIOSTATISTICA');

--DOCTORAT DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 7, 'MATEMATICA');

--DOCTORAT DOMENIUL INFO
INSERT INTO study_program VALUES(nextval('study_program_seq'), 8, 'INFORMATICA');

---------------------------------------------------------------------------------------------------------------------
--LICENTA DOMENIUL MATE PROGRAM MATEMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 3');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA APLICATA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 2, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 2, 'ANUL 3');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 3, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 3, 'ANUL 3');

--LICENTA DOMENIUL INFO PROGRAM INFORMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 3');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 4');

--MASTER DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 6, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 6, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 7, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 7, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 8, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 8, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 9, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 9, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM INTELIGENTA ARTIFICIALA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 10, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 10, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM INGINERIE SOFTWARE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 11, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 11, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM SECURITATE SI LOGICA APLICATA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 12, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 12, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM SISTEME DISTRIBUITE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 13, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 13, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM DATA SCIENCE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 14, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 14, 'ANUL 2');

--MASTER DOMENIUL MATE PROGRAM PROCESAREA LIMBAJULUI NATURAL
INSERT INTO study_year VALUES(nextval('study_year_seq'), 15, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 15, 'ANUL 2');

--MASTER DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 16, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 16, 'ANUL 2');

--DOCTORAT DOMENIUL MATE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 4');

--DOCTORAT DOMENIUL INFO
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 4');

-------------------------------------------------------------------------------------------------------
--LICENTA DOMENIUL MATE PROGRAM MATEMATICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 1, 'IF');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA APLICATA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 2, 'IF');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 3, 'IF');

--LICENTA DOMENIUL INFO PROGRAM INFORMATICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 4, 'IF');
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 4, 'ID');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 5, 'IF');

--MASTER DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 6, 'IF');

--MASTER DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 7, 'IF');

--MASTER DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 8, 'IF');

--MASTER DOMENIUL MATE PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 9, 'IF');
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 9, 'IFR');

--MASTER DOMENIUL MATE PROGRAM INTELIGENTA ARTIFICIALA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 10, 'IF');

--MASTER DOMENIUL MATE PROGRAM INGINERIE SOFTWARE
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 11, 'IF');

--MASTER DOMENIUL MATE PROGRAM SECURITATE SI LOGICA APLICATA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 12, 'IF');

--MASTER DOMENIUL MATE PROGRAM SISTEME DISTRIBUITE
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 13, 'IF');

--MASTER DOMENIUL MATE PROGRAM DATA SCIENCE
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 14, 'IF');

--MASTER DOMENIUL MATE PROGRAM PROCESAREA LIMBAJULUI NATURAL
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 15, 'IF');

--MASTER DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 16, 'IF');

--DOCTORAT DOMENIUL MATE
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 17, 'IF');

--DOCTORAT DOMENIUL INFO
INSERT INTO learning_type VALUES(nextval('learning_type_seq'), 18, 'IF');

--------------------------------------------------------------------------------------------------------
--LICENTA DOMENIUL MATE PROGRAM MATEMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '101');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '102');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '103');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '104');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '111');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '112');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '113');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 2, '201');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 2, '202');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 3, '301');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA APLICATA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 4, '221');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 4, '222');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA APLICATA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 5, '321');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 5, '322');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 6, '211');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 6, '212');

--LICENTA DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 7, '311');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 7, '312');

--LICENTA DOMENIUL INFO PROGRAM INFORMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '131');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '132');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '133');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '134');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '141');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '142');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '143');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '144');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '151');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 8, '152');

--LICENTA DOMENIUL INFO PROGRAM INFORMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '231');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '232');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '233');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '234');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '241');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '242');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '243');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '244');

--LICENTA DOMENIUL INFO PROGRAM INFORMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '331');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '332');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '333');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '334');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '341');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '342');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '343');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '344');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '161');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '162');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '163');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '164');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '251');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '252');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '253');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '254');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '351');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '352');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '353');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '354');

--LICENTA DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '451');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '452');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '453');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '454');

--MASTER DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 15, '401');

--MASTER DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 16, '501');

--MASTER DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 17, '402');

--MASTER DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 18, '502');

--MASTER DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 19, '403');

--MASTER DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 20, '503');

--MASTER DOMENIUL MATE PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 21, '405');

--MASTER DOMENIUL MATE PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 22, '505');

--MASTER DOMENIUL MATE PROGRAM INTELIGENTA ARTIFICIALA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 23, '407');

--MASTER DOMENIUL MATE PROGRAM INTELIGENTA ARTIFICIALA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 24, '507');

--MASTER DOMENIUL MATE PROGRAM INGINERIE SOFTWARE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 25, '406');

--MASTER DOMENIUL MATE PROGRAM INGINERIE SOFTWARE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 26, '506');

--MASTER DOMENIUL MATE PROGRAM SECURITATE SI LOGICA APLICATA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 27, '410');

--MASTER DOMENIUL MATE PROGRAM SECURITATE SI LOGICA APLICATA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 28, '510');

--MASTER DOMENIUL MATE PROGRAM SISTEME DISTRIBUITE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 29, '408');

--MASTER DOMENIUL MATE PROGRAM SISTEME DISTRIBUITE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 30, '508');

--MASTER DOMENIUL MATE PROGRAM DATA SCIENCE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 31, '411');

--MASTER DOMENIUL MATE PROGRAM DATA SCIENCE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 32, '511');

--MASTER DOMENIUL MATE PROGRAM PROCESAREA LIMBAJULUI NATURAL ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 33, '412');

--MASTER DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 34, 'BIO_1');

--MASTER DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 35, 'BIO_2');

--DOCTORAT DOMENIUL MATE PROGRAM MATE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 36, 'DOCT_MATE_1');
--DOCTORAT DOMENIUL MATE PROGRAM MATE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 37, 'DOCT_MATE_2');
--DOCTORAT DOMENIUL MATE PROGRAM MATE ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 38, 'DOCT_MATE_3');
--DOCTORAT DOMENIUL MATE PROGRAM MATE ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 39, 'DOCT_MATE_4');

--DOCTORAT DOMENIUL MATE PROGRAM INFO ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 40, 'DOCT_INFO_1');
--DOCTORAT DOMENIUL MATE PROGRAM INFO ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 41, 'DOCT_INFO_2');
--DOCTORAT DOMENIUL MATE PROGRAM INFO ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 42, 'DOCT_INFO_3');
--DOCTORAT DOMENIUL MATE PROGRAM INFO ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 43, 'DOCT_INFO_4');