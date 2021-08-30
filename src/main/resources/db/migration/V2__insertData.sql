--PASSWORD IS PASSWORD AND IS HASHED
INSERT INTO users VALUES(nextval('users_seq'), 'ADMIN', 'admin@my.fmi.unibuc.ro', '$2y$12$jbkinGYV9wduwiF4HFHOI.D/Vu6PH.tMPEiaVsYZVbLrIMv7Rou8C', true);

------------------------------------------------------------------------------------------------------------------------
INSERT INTO learning_type VALUES(nextval('learning_type_seq'),'IF');
INSERT INTO learning_type VALUES(nextval('learning_type_seq'),'ID');
INSERT INTO learning_type VALUES(nextval('learning_type_seq'),'IFR');

------------------------------------------------------------------------------------------------
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 1, 'LICENTA');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 2, 'LICENTA');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 1, 'MASTER');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 3, 'MASTER');
INSERT INTO university_study_type VALUES(nextval('university_study_type_seq'), 1, 'DOCTORAT');

------------------------------------------------------------------------------------------------------------------------
-- --LICENTA IF
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'INFORMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 1, 'CALCULATOARE SI TEHNOLOGIA INFORMATIEI');
--
-- -- LICENTA ID
INSERT INTO domain VALUES(nextval('domain_seq'), 2, 'INFORMATICA');

-- -- MASTER IF
INSERT INTO domain VALUES(nextval('domain_seq'), 3, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 3, 'INFORMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 3, 'BIOSTATISTICA');

-- -- MASTER IFR
INSERT INTO domain VALUES(nextval('domain_seq'), 4, 'INFORMATICA');
--
-- -- DOCTORAT IF
INSERT INTO domain VALUES(nextval('domain_seq'), 5, 'MATEMATICA');
INSERT INTO domain VALUES(nextval('domain_seq'), 5, 'INFORMATICA');
--
------------------------------------------------------------------------------------------------------------------------
-- -- LICENTA IF DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA APLICATA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 1, 'MATEMATICA INFORMATICA');
--
-- -- LICENTA IF DOMENIUL INFO
INSERT INTO study_program VALUES(nextval('study_program_seq'), 2, 'INFORMATICA');
--
-- -- LICENTA IF DOMENIUL CTI
INSERT INTO study_program VALUES(nextval('study_program_seq'), 3, 'CALCULATOARE SI TEHNOLOGIA INFORMATIEI');
--
-- -- LICENTA ID DOMENIUL INFO
INSERT INTO study_program VALUES(nextval('study_program_seq'), 4, 'INFORMATICA');
--
-- -- MASTER IF DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'STUDII AVANSATE IN MATEMATICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'MATEMATICA DIDACTICA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 5, 'PROBABILITATI SI STATISTICA');
--
-- -- MASTER IF DOMENIUL INFORMATICA
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'BAZE DE DATE SI TEHNOLOGII SOFTWARE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'INTELIGENTA ARTIFICIALA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'INGINERIE SOFTWARE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'SECURITATE SI LOGICA APLICATA');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'SISTEME DISTRIBUITE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'DATA SCIENCE');
INSERT INTO study_program VALUES(nextval('study_program_seq'), 6, 'PROCESAREA LIMBAJULUI NATURAL');
--
-- -- MASTER IF DOMENIUL BIOSTATISTICA
INSERT INTO study_program VALUES(nextval('study_program_seq'), 7, 'BIOSTATISTICA');
--
-- -- MASTER IFR DOMENIUL INFORMATICA
INSERT INTO study_program VALUES(nextval('study_program_seq'), 8, 'BAZE DE DATE SI TEHNOLOGII SOFTWARE');
--
-- -- DOCTORAT IF DOMENIUL MATE
INSERT INTO study_program VALUES(nextval('study_program_seq'), 9, 'MATEMATICA');
--
-- -- DOCTORAT IF DOMENIUL INFO
INSERT INTO study_program VALUES(nextval('study_program_seq'), 10, 'INFORMATICA');
--
------------------------------------------------------------------------------------------------------------------------
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 1, 'ANUL 3');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA APLICATA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 2, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 2, 'ANUL 3');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 3, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 3, 'ANUL 3');
--
-- -- LICENTA IF DOMENIUL INFO PROGRAM INFORMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 4, 'ANUL 3');
--
-- -- LICENTA IF DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 5, 'ANUL 4');
--
-- -- LICENTA ID DOMENIUL INFO PROGRAM INFORMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 6, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 6, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 6, 'ANUL 3');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 7, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 7, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 8, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 8, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 9, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 9, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 10, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 10, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INTELIGENTA ARTIFICIALA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 11, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 11, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INGINERIE SOFTWARE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 12, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 12, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SECURITATE SI LOGICA APLICATA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 13, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 13, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SISTEME DISTRIBUITE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 14, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 14, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM DATA SCIENCE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 15, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 15, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM PROCESAREA LIMBAJULUI NATURAL
INSERT INTO study_year VALUES(nextval('study_year_seq'), 16, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 16, 'ANUL 2');
--
-- -- MASTER IF DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 17, 'ANUL 2');
--
-- -- MASTER IFR DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 18, 'ANUL 2');
--
-- -- DOCTORAT IF DOMENIUL MATE
INSERT INTO study_year VALUES(nextval('study_year_seq'), 19, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 19, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 19, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 19, 'ANUL 4');
--
-- -- DOCTORAT IF DOMENIUL INFO
INSERT INTO study_year VALUES(nextval('study_year_seq'), 20, 'ANUL 1');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 20, 'ANUL 2');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 20, 'ANUL 3');
INSERT INTO study_year VALUES(nextval('study_year_seq'), 20, 'ANUL 4');
--
-- --------------------------------------------------------------------------------------------------------
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '101');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '102');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '103');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '104');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '111');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '112');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 1, '113');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 2, '201');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 2, '202');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 3, '301');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA APLICATA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 4, '221');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 4, '222');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA APLICATA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 5, '321');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 5, '322');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 6, '211');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 6, '212');
--
-- -- LICENTA IF DOMENIUL MATE PROGRAM MATEMATICA INFORMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 7, '311');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 7, '312');
--
-- -- LICENTA IF DOMENIUL INFO PROGRAM INFORMATICA ANUL 1
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
--
-- -- LICENTA IF DOMENIUL INFO PROGRAM INFORMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '231');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '232');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '233');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '234');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '241');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '242');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '243');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 9, '244');
--
-- -- LICENTA IF DOMENIUL INFO PROGRAM INFORMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '331');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '332');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '333');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '334');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '341');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '342');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '343');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 10, '344');
--
-- -- LICENTA IF DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '161');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '162');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '163');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 11, '164');
--
-- -- LICENTA IF DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '251');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '252');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '253');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 12, '254');
--
-- -- LICENTA IF DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '351');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '352');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '353');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 13, '354');
--
-- -- LICENTA IF DOMENIUL CTI PROGRAM CALCULATOARE SI TEHNOLOGIA INFORMATIEI ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '451');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '452');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '453');
INSERT INTO study_group VALUES(nextval('study_group_seq'), 14, '454');
--
-- -- LICENTA ID DOMENIUL INFO PROGRAM INFORMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 15, 'INFO_DIST_1');
--
-- -- LICENTA ID DOMENIUL INFO PROGRAM INFORMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 16, 'INFO_DIST_2');
--
-- -- LICENTA ID DOMENIUL INFO PROGRAM INFORMATICA ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 17, 'INFO_DIST_3');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 18, '401');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM STUDII AVANSATE IN MATEMATICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 19, '501');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 20, '402');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM MATEMATICA DIDACTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 21, '502');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 22, '403');
--
-- -- MASTER IF DOMENIUL MATE PROGRAM PROBABILITATI SI STATISTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 23, '503');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 24, '405');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 25, '505');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INTELIGENTA ARTIFICIALA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 26, '407');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INTELIGENTA ARTIFICIALA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 27, '507');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INGINERIE SOFTWARE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 28, '406');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM INGINERIE SOFTWARE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 29, '506');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SECURITATE SI LOGICA APLICATA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 30, '410');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SECURITATE SI LOGICA APLICATA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 31, '510');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SISTEME DISTRIBUITE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 32, '408');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM SISTEME DISTRIBUITE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 33, '508');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM DATA SCIENCE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 34, '411');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM DATA SCIENCE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 35, '511');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM PROCESAREA LIMBAJULUI NATURAL ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 36, '412');
--
-- -- MASTER IF DOMENIUL INFO PROGRAM PROCESAREA LIMBAJULUI NATURAL ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 37, '512');
--
-- -- MASTER IF DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 38, 'BIO_1');
--
-- -- MASTER IF DOMENIUL BIOSTATISTICA PROGRAM BIOSTATISTICA ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 39, 'BIO_2');
--
-- -- MASTER ID DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 40, 'BAZE_IFR_1');
--
-- -- MASTER ID DOMENIUL INFO PROGRAM BAZE DE DATE SI TEHNOLOGII SOFTWARE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 41, 'BAZE_IFR_2');
--
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM MATE ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 42, 'DOCT_MATE_1');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM MATE ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 43, 'DOCT_MATE_2');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM MATE ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 44, 'DOCT_MATE_3');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM MATE ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 45, 'DOCT_MATE_4');
--
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM INFO ANUL 1
INSERT INTO study_group VALUES(nextval('study_group_seq'), 46, 'DOCT_INFO_1');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM INFO ANUL 2
INSERT INTO study_group VALUES(nextval('study_group_seq'), 47, 'DOCT_INFO_2');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM INFO ANUL 3
INSERT INTO study_group VALUES(nextval('study_group_seq'), 48, 'DOCT_INFO_3');
-- -- DOCTORAT IF DOMENIUL MATE PROGRAM INFO ANUL 4
INSERT INTO study_group VALUES(nextval('study_group_seq'), 49, 'DOCT_INFO_4');
