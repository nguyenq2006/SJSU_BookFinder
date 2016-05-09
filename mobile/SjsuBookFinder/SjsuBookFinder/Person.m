//
//  Person.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/14/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "Person.h"

/// Class to model a student
@implementation Person

/**
 *  Standard class constructor
 *
 *  @return object for this class
 */
-(instancetype)init{
    return self;
}

/**
 *  Overloaded contructor for Person class
 *
 *  @param firstName user first name
 *  @param lastName  user last name
 *  @param sjsuId    user sjsu id
 *  @param password  user password
 *
 *  @return the object for this class
 */
-(instancetype)initWithFirstName:(NSString *)firstName withLastName:(NSString *)lastName withSjsuId:(NSString *)sjsuId withPassword:(NSString *)password{
    _firstName = firstName;
    _lastName = lastName;
    _sjsuID = sjsuId;
    _password = password;
    return self;
}

/**
 *  Serialize the Person object for this class
 *
 *  @return the serialized string
 */
-(NSString *)stringify{
    NSString *result = [NSString stringWithFormat:@"%@,%@,%@,%@",self.firstName, self.lastName,self.sjsuID,self.password];
    return result;
}

/**
 *  Class method to deserialize a Person object
 *
 *  @param personString the serialized String
 *
 *  @return deserialized Person object
 */
+(Person *)unstringify:(NSString *)personString{
    Person *p = [[Person alloc]init];
    NSArray *strArray = [personString componentsSeparatedByString:@","];
    p.firstName = strArray[0];
    p.lastName = strArray[1];
    p.sjsuID = strArray[2];
    p.password = strArray[3];
    return p;
}

@end
