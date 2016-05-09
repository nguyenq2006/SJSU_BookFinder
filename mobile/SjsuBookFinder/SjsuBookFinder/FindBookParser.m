//
//  FindBookParser.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 5/2/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import "FindBookParser.h"
/**
 *  Parser class to parse the findbook response from the server
 */
@interface FindBookParser()
@property (strong, nonatomic) NSString *response;
@end

@implementation FindBookParser

/**
 *  Standard contructor function
 *
 *  @return object of this class
 */
-(id)init{
    return self;
}

/**
 *  Overloaded contructor
 *
 *  @param response server response string
 *
 *  @return object of this class
 */
-(instancetype)initWithServerResponse:(NSString *)response{
    _response = response;
    [self parseResponse];
    return self;
}

/**
 *  Method to parse the response recieved from the server
 */
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
