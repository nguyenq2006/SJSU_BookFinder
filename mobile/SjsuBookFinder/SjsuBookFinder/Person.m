//
//  Person.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/14/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "Person.h"

@implementation Person

-(instancetype)init{
    return self;
}

-(instancetype)initWithFirstName:(NSString *)firstName withLastName:(NSString *)lastName withSjsuId:(NSString *)sjsuId withPassword:(NSString *)password{
    _firstName = firstName;
    _lastName = lastName;
    _sjsuID = sjsuId;
    _password = password;
    return self;
}

-(NSString *)stringify{
    NSString *result = @"";
    return result;
}
-(Person *)unstringify:(NSString *)personString{
    Person *p = [[Person alloc]init];
    return p;
}

@end
