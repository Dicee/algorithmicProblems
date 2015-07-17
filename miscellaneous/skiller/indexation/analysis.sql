# most popular words
select 
	word, count(word) as usages
from words 
where length(word) >= 4
group by word 
order by usages desc
limit 200;

# total number of words
select count(word) from words;

# rank of the word skiller
select count(*) as skiller_rank from
	(select word, count(word) as skiller_usages from words where word like ('%skill%')) as t1,
	(select word, count(word) as usages from words where length(word) >= 3 group by word) as t2
where
	t2.usages > t1.skiller_usages;

# usages of skiller or derivated words
select word, count(word) as skiller_usages from words where word like ('%skill%');

# biggest questioners
select 
	u.id, login, count(author) as questions 
from users as u left join questions as q on login=author 
group by login 
order by questions desc;

# biggest commenters
select 
	u.id, login, count(author) as comments 
from users as u left join comments as q on login=author 
group by login 
order by comments desc;

# biggest contributers
select 
	q_id as id,
	login,
	questions,
	comments,
	questions + comments as total
from 
		(select 
			u.id as q_id, login, author, count(author) as questions 
		from users as u left join questions as q on login=author 
		group by login) as t1
	left join
		(select 
			u.id as c_id, count(author) as comments 
		from users as u left join comments as c on login=author 
		group by login) as c
	on q_id=c_id
order by total desc;

# total number of users
select count(*) as number_of_users from users;

# users who have never posted a question
select
	count(*) as users_who_never_posted_a_question
from 
	(select 
		u.id as q_id, login, author, count(author) as questions 
	from users as u left join questions as q on login=author 
	group by login) as q
where
	questions=0;

# users who have never posted an answer
select
	count(*) as users_who_never_posted_an_answer
from 
	(select 
		u.id as q_id, login, author, count(author) as comments 
	from users as u left join comments as q on login=author 
	group by login) as c
where
	comments=0;

# users who have never posted at all
select
	count(*) as users_who_never_posted_at_all
from 
		(select 
			u.id as q_id, count(author) as questions 
		from users as u left join questions as q on login=author 
		group by login) as t1
	left join
		(select 
			u.id as c_id, count(author) as comments 
		from users as u left join comments as c on login=author 
		group by login) as c
	on q_id=c_id
where
	questions=0 and comments=0;


# total contributions
select quest as questions, comm as comments, quest + comm as posts 
from 
	(select count(*) as quest from questions) as q,
	(select count(*) as comm  from comments ) as c;

# most popular questions
select 
	concat('http://skiller.fr/question/',q.id) as id,
	q.author as author, 
	count(q.id) as comments
from 
	questions as q right join comments as c on q.id=c.question_id
group by id
order by comments desc;