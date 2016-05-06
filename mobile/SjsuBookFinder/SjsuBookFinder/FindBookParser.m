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
    if ([responseArray[0] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]] != nil) {
        _sjsuId = [responseArray[0] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    }
    
    if ([responseArray[1] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]]) {
        _isbn = [responseArray[1] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    }
    
    if ([responseArray[2] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]] != nil) {
        _bookTitle = [responseArray[2] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    }
    
    if ([responseArray[3] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]] != nil) {
        _price = [responseArray[3] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    }
}

@end
