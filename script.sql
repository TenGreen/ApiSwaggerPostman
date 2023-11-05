--1. Получить всех студентов, возраст которых находится между 10 и 20 (можно подставить любые числа, главное, чтобы нижняя граница была меньше верхней).
select * from student s where s.age between 10 and 20;
--2. Получить всех студентов, но отобразить только список их имен.
select name from student;
--3. Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).
select * from student s where s.name like '%x%';
--4. Получить всех студентов, у которых возраст меньше идентификатора.
select * from student s where s.age <s.id ;
--5. Получить всех студентов упорядоченных по возрасту.
select * from student s order by age ;
