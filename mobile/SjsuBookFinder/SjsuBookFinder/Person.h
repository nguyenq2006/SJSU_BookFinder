//
//  Person.h
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 3/14/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import <Foundation/Foundation.h>

/// Class to model a student
@interface Person : NSObject
@property (nonatomic, strong) NSString *firstName;
@property (nonatomic, strong) NSString *lastName;
@property (nonatomic, strong) NSString *sjsuID;
@property (nonatomic, strong) NSString *password;

-(instancetype)init;
-(instancetype)initWithFirstName:(NSString *)firstName withLastName:(NSString *)lastName withSjsuId:(NSString *)sjsuId withPassword:(NSString *)password;

-(NSString *)stringify;
+(Person *)unstringify:(NSString *)personString;

@end
