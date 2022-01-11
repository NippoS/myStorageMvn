create table if not exists students
(
    id                 bigint           auto_increment primary key,
    last_name          varchar(50)      not null,
    status             varchar(15)      not null
);

create table if not exists subjects
(
    id                 bigint           auto_increment primary key,
    name               varchar(100)     not null,
    status             varchar(15)      not null
);

create table if not exists marks
(
    id                 bigint           auto_increment primary key,
    mark               bigint           not null,
    student_id         bigint           not null,
    subject_id         bigint           not null,
    status             varchar(15)      not null
);

create table if not exists students_subjects
(
    student_id    bigint    not null,
    subject_id    bigint    not null,

    constraint students_subjects_student_id__fk
        foreign key (student_id)
            references students (id)
            on delete cascade,

    constraint students_subjects_subject_id__fk
            foreign key (subject_id)
                references subjects (id)
                on delete cascade,

    constraint student_subject UNIQUE (student_id, subject_id)
);