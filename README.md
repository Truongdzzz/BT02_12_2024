#get all user
query{
  findAllUsers {
    id,
    fullname,
    email
  }
}
#get all category
query{
  findAllCategories{
    id,
    name,
    images
  }
}
#get all category by userId
query{
getCategoriesByUser(
	userId: 1
) {
  name
}
}
# create user
mutation {
  createUser(
    fullname:"aa",
    email:"aa",
    password:"12321",
    phone:"01023123"
  ) {
    id
  }
}
# update user
mutation{
  updateUser(
    id:1,
    fullname:"aaa",
    email:"111",
    phone:"asdasd"
  )
  {
    id
  }
}
#read user
query{
  findAllCategories{
    id,
    name,
    images
  }
}
# delete user
mutation{
  deleteUser(id: 10)
}
