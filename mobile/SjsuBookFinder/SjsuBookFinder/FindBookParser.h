//
//  FindBookParser.h
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 5/2/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FindBookParser : NSObject
@property (strong, nonatomic) NSString *sjsuId;
@property (strong, nonatomic) NSString *isbn;
@property (strong, nonatomic) NSString *bookTitle;

-(instancetype)initWithServerResponse:(NSString *)response;

@end
