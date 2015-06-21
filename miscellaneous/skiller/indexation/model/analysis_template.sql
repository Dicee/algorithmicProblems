# @query(name="MOST_POPULAR_WORDS", params="minLength,limit", description="Most popular words of length greater than ${minLength} on Skiller")
select 
	word, count(word) as usages
from words 
where length(word) >= %d
group by word 
order by usages desc
limit %d;

# @query(name="TOTAL_WORDS", description="Total number of words used on Skiller")
select count(word) from words;

# @query(name="SKILLER_RANK", params="minLength", description="Rank of the word 'Skiller' (and all derivated words) among the words of length greater than ${minLength}")
select count(*) as skiller_rank from
	(select word, count(word) as skiller_usages from words where word like ('%skill%')) as t1,
	(select word, count(word) as usages from words where length(word) >= %d group by word) as t2
where
	t2.usages > t1.skiller_usages;

# @query(name="FAMILY_RANK", params="family,minLength", description="Rank of the family of words [${family}] among the words of length greater than ${minLength}")
select count(*) as rank from
	(select word, count(word) as usages from words where word like ('%s')) as t1,
	(select word, count(word) as w_usages from words where length(word) >= %d group by word) as t2
where
	t2.w_usages > t1.usages;	
	
# @query(name="SKILLER_USAGES", description="Usages of the word 'Skiller' or derivated words")
select word, count(word) as skiller_usages from words where word like ('%skill%');

# @query(name="FAMILY_USAGES", params="family", description="Usages of the words of the family ${family}")
select word, count(word) as usages from words where word like ('%s');

# @query(name="BIGGEST_QUESTIONERS", description="Users who posted the most answers")
select 
	u.id, login, count(author) as questions 
from users as u left join questions as q on login=author 
group by login 
order by questions desc;

# @query(name="BIGGEST_ANSWERERS", description="Users who posted the most questions")
select 
	u.id, login, count(author) as comments 
from users as u left join comments as q on login=author 
group by login 
order by comments desc;

# @query(name="BIGGEST_CONTRIBUTERS", description="Users who contributed the most")
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

# @query(name="NUMBER_OF_USERS", description="Users who contributed the most")
select count(*) as number_of_users from users;

# @query(name="NEVER_POSTED_QUESTION", description="Users who have never posted a question")
select
	count(*) as users_who_never_posted_a_question
from 
	(select 
		u.id as q_id, login, author, count(author) as questions 
	from users as u left join questions as q on login=author 
	group by login) as q
where
	questions=0;

# @query(name="NEVER_POSTED_ANSWER", description="Users who have never posted an answer")
select
	count(*) as users_who_never_posted_an_answer
from 
	(select 
		u.id as q_id, login, author, count(author) as comments 
	from users as u left join comments as q on login=author 
	group by login) as c
where
	comments=0;

# @query(name="NEVER_POSTED_ANYTHING", description="Users who have never posted at all")
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


# @query(name="TOTAL_CONTRIBUTIONS", description="Total number of posts")
select quest as questions, comm as comments, quest + comm as posts 
from 
	(select count(*) as quest from questions) as q,
	(select count(*) as comm  from comments ) as c;

# @query(name="MOST_POPULAR_QUESTIONS", description="Most popular questions")
select 
	concat('http://skiller.fr/question/',q.id) as id,
	q.author as author, 
	count(q.id) as comments
from 
	questions as q right join comments as c on q.id=c.question_id
group by id
order by comments desc;
