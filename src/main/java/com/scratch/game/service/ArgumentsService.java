package com.scratch.game.service;

import com.scratch.game.domain.Arguments;
import com.scratch.game.errors.ScratchGameException;

public interface ArgumentsService {

    Arguments parseArguments(String[] args) throws ScratchGameException;
}
