CREATE UNIQUE INDEX CONCURRENTLY allocation_unique
ON secretary_allocation (learning_type_id, university_study_type_id, domain_id, study_program_id, study_year_id);