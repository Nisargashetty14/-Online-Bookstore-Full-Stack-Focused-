INSERT INTO books (title, author, description, price, image_url) VALUES
('Wise Steps For Success', 'AUTHOR', 'Practical steps and habits to help you build momentum, stay consistent, and reach your goals.', 19.99,
 '/img/wise-steps-for-success.png'),
('The Boy', 'Unknown', 'A reflective story of growing up and finding your way, told through a quiet, memorable journey.', 14.99,
 '/img/the-boy.png'),
('BOOK TITLE', 'AUTHOR NAME', 'A moody, atmospheric story about a lone traveler and the road ahead.', 16.50,
 '/img/book-title.png'),
('Your Sample Text Here', 'Lorem Ipsum', 'A colorful, modern cover for sample content and demo layouts.', 11.25,
 '/img/sample-text-here.png'),
('SPOKEN ENGLISH', 'Author Name', 'A practical guide to improve spoken English with clear practice and everyday examples.', 12.75,
 '/img/spoken-english.png');

INSERT INTO reviews (book_id, author_name, comment, rating) VALUES
(1, 'Sam', 'Changed how I name variables and structure modules.', 5),
(1, 'Jordan', 'Dense but worth it — keep it on your desk.', 4),
(2, 'Riya', 'Every Java dev should read the generics chapter.', 5),
(3, 'Chris', 'Great intro if you are new to Spring Boot.', 4);
