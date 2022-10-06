# library-management

Different Role / User 
    1. Student
    2. Admin
    3. Librarian
    
Different Calls / HttpMethods
      Calls            Endpoints
    1. POST -----> /registrations -------> public + Unauthenticated (Open for all the User to Register themsselves)
    2. POST -----> /authenticate  -------> public + Unauthenticated (Open for all the Users to login with their credentials(username + password) )
    3. GET  -----> /books         -------> Authenticated public (All the registered User after login with their credentials can access this)
    4. POST -----> /books         -------> Authenticated public (Only Admin and Librarian can Use this after login with their credentials)
    5. GET  -----> /users         -------> Authenticated public (This will retrieve all the registered User List and only accessible to ADMIN)

Different Tables 
    1. User----> It contains User(Admin / Librarian / Student) Details
    2. Book----> It is book list
    
Different Layers
    1. Controller Layer ----> Models-----> Request-----> What ever data user passes will be in the form of request
                                    -----> Response----> After processing all the steps User will get data in the form of response
                        ----> Will Convert data into DTOs and pass to the Service Layer
                        ----> Will Talk to the Service Layer
                        
    2. Service Layer -------> Will Take the data in the form of DTOs 
                     -------> Will talk to the DataBase
                     -------> Will Fetch the data from DataBase in the form of Entity
                     -------> Will return back data to Controller in the form of DTOs
                     
    3. Repository Layer -----> Will Take data in the form of Entity
     
    4. Security Layer -------> Used 2 Filters 
                                          1. AuthTokenFilter -----> After authetication with username and password jwt token will be generated and this token 
                                                                    will be validate in this filter by calling a method call generateToken in JWTUtils class
                                                                    
                                          2. LoggedInContextFilter-> To hold the current User until the token expires
                                                                  
    
    
