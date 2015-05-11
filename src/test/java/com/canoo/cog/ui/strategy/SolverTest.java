package com.canoo.cog.ui.strategy;

/*
 * #%L
 * code-of-gotham
 * %%
 * Copyright (C) 2015 Canoo Engineering AG
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.Arrays;

import com.canoo.cog.sonar.SonarService;
import com.canoo.cog.sonar.model.CityModel;
import org.junit.Test;

public class SolverTest {

    @Test
    public void testSolveProblem() throws Exception {
        Solver solver = new SimplestSolverEver();
        CityNode building1 = new CityNode(5);
        CityNode building2 = new CityNode(5);
        CityNode building3 = new CityNode(5);
        CityNode building4 = new CityNode(5);
        CityNode city = new CityNode(Arrays.asList(building1, building2, building3, building4));

        solver.solveProblem(city, 1);

        System.out.println(city.toString());

    }

    @Test
    public void testSolveProblemRecursive() throws Exception {
        Solver solver = new SimplestSolverEver();

        CityNode hood1 = new CityNode(Arrays.asList(new CityNode(5), new CityNode(2), new CityNode(7), new CityNode(1)));
        CityNode hood2 = new CityNode(Arrays.asList(hood1, new CityNode(10), new CityNode(2), new CityNode(6), new CityNode(4)));
        CityNode hood3 = new CityNode(Arrays.asList(new CityNode(1), new CityNode(1), new CityNode(8), new CityNode(15)));

        CityNode city = new CityNode(Arrays.asList(hood2,hood3));

        solver.solveProblem(city,1);

        System.out.println(city.toString());

    }

    @Test
    public void testSolveBetterProblem() throws Exception {
        Solver solver = new LittleBetterSolverEver();
        CityNode building1 = new CityNode(5);
        CityNode building2 = new CityNode(5);
        CityNode building3 = new CityNode(5);
        CityNode building4 = new CityNode(5);
        CityNode city = new CityNode(Arrays.asList(building1, building2, building3, building4));

        solver.solveProblem(city,1);

        System.out.println(city.toString());

    }

    @Test
    public void testSolveBetterProblemRecursiveNotSoDifficult() throws Exception {
        Solver solver = new LittleBetterSolverEver();

        CityNode hood1 = new CityNode(Arrays.asList(new CityNode(2), new CityNode(6), new CityNode(4)));
        CityNode hood2 = new CityNode(Arrays.asList(new CityNode(1), new CityNode(1), new CityNode(8)));

        CityNode city = new CityNode(Arrays.asList(hood1,hood2));

        solver.solveProblem(city,1);

        System.out.println(city.toString());

    }

    @Test
    public void testSolveBetterProblemRecursive() throws Exception {
        Solver solver = new LittleBetterSolverEver();

        CityNode hood1 = new CityNode(Arrays.asList(new CityNode(5), new CityNode(2), new CityNode(7), new CityNode(1)));
        CityNode hood2 = new CityNode(Arrays.asList(hood1, new CityNode(10), new CityNode(2), new CityNode(6), new CityNode(4)));
        CityNode hood3 = new CityNode(Arrays.asList(new CityNode(1), new CityNode(1), new CityNode(8), new CityNode(15)));

        CityNode city = new CityNode(Arrays.asList(hood2,hood3));

        solver.solveProblem(city,1);

        System.out.println(city.toString());

    }

    @Test
    public void testRealCity() throws Exception {
        Solver solver = new LittleBetterSolverEver();

        SonarService sonarService = new SonarService();
        sonarService.setSonarSettings("http://localhost:9000/", "", "","");
        CityModel cityData = sonarService.getCityData("code-of-gotham:code-of-gotham");
        CityNode cityNode = new SonarToStrategyConerter().convertCityToNode(cityData);
        solver.solveProblem(cityNode, 1);
        System.out.println(cityNode.toStringWithNames());

    }
}