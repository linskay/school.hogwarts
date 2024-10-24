package
ru.hogwarts.school;

select *
from student;
select *
from student
where age > 14
  and age < 18;
select name
from student;
select *
from student
where name LIKE '%o%';
select *
from student
where age < id;
select *
from student
order by age;