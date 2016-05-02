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
    [self parseResponse];
    return self;
}

-(void)parseResponse{
    NSArray<NSString *> *responseArray = [_response componentsSeparatedByString:@"*&#$&!@#"];
    _sjsuId = responseArray[0];
    _isbn = responseArray[1];
    _bookTitle = responseArray[2];
}

@end
