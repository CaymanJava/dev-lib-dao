REST API examples:

POST request to save author:
curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"Cay Horstmann"}' http://localhost:5674/author/save

POST request to update author:
curl -i -X POST -H 'Content-Type: application/json' -d '{"id":"48", "name":"Cay Horstmann"}' http://localhost:5674/author/update

POST request to save book:
curl -i -X POST -H 'Content-Type: application/json' -d
'{
"name":"SQL for dumps",
"language":"RU",
"year":"2013",
"authors":["dBosenko", "Cay Horstmann"],
"publisher":"ASMA",
"image":"kartinka",
"fileId":"http://google.doc.com/fileId",
"description":"The best SQL book, you ever seen",
"categoryId":"19",
"pageCount":"452"
}'  http://localhost:5674/book/save


POST request to update book:
curl -i -X POST -H 'Content-Type: application/json' -d
'{
"id":"32",
"name":"SQL for dumps",
"language":"RU",
"year":"2013",
"authors":["dBosenko", "Cay Horstmann"],
"publisher":"ASMA",
"image":"kartinka",
"fileId":"http://google.doc.com/fileId",
"description":"The best SQL book, you ever seen",
"categoryId":"19",
"pageCount":"452"
}'  http://localhost:5674/book/update

POST request to save category:
curl -i -X POST -H 'Content-Type: application/json' -d '{"name":"SQL", "image":"http://image.url"}' http://localhost:5674/category/save

POST request to update category:
curl -i -X POST -H 'Content-Type: application/json' -d '{"id":"21", ""name":"SQLчик", "image":"http://image.url"}' http://localhost:5674/category/update

POST request to filter data:
curl -i -X POST -H 'Content-Type: application/json' -d '{"from":"2000","to":"2016", "lang":"RU", "categoryId":"19"}' http://localhost:5674/filter

POST request to search data:
curl -i -X POST -H 'Content-Type: application/json' -d '{"keyword":"java"}' http://localhost:5674/search

POST request to add new vote:
curl -i -X POST -H 'Content-Type: application/json' -d '{"userId":"1", "bookId":"29", "ratingId":"29", "value":"5"}' http://localhost:5674/rating/save
