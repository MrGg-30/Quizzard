db.createUser({
    user: "quizzard",
    pwd: "quizzard",
    roles: [
        {
            role: "readWrite",
            db: "quizzard"
        }
    ]
});

db = db.getSiblingDB("quizzard");

db.createCollection("quiz");
