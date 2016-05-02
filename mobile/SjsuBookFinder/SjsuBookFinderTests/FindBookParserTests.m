//
//  FindBookParserTests.m
//  SjsuBookFinder
//
//  Created by Ravin Sardal on 5/2/16.
//  Copyright © 2016 Ravin Sardal. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "FindBookParser.h"

@interface FindBookParserTests : XCTestCase

@end

@implementation FindBookParserTests

- (void)setUp {
    [super setUp];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
}

-(void)testParse{
    NSString *response = @"0933834 *&#$&!@# 3847837483 *&#$&!@# DataStructsAndAlgo";
    FindBookParser *parser = [[FindBookParser alloc]initWithServerResponse:response];
    XCTAssertTrue([@"0933834" isEqualToString:parser.sjsuId]);
    XCTAssertTrue([@"3847837483" isEqualToString:parser.isbn]);
    XCTAssertTrue([@"DataStructsAndAlgo" isEqualToString:parser.bookTitle]);
}
@end
