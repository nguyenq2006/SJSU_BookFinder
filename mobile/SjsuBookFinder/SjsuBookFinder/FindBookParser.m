//
//  FindBookParser.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 5/2/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "FindBookParser.h"

@interface FindBookParser()
@property (strong, nonatomic) NSString *response;
@end

@implementation FindBookParser

-(id)init{
    return self;
}

-(instancetype)initWithServerResponse:(NSString *)response{
    _response = response;
    return self;
}



@end
