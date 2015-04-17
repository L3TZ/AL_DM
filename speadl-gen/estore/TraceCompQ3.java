package estore;

import question3.interfaces.ITrace;

@SuppressWarnings("all")
public abstract class TraceCompQ3 {
  public interface Requires {
  }
  
  public interface Component extends TraceCompQ3.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ITrace trace();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements TraceCompQ3.Component, TraceCompQ3.Parts {
    private final TraceCompQ3.Requires bridge;
    
    private final TraceCompQ3 implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_trace() {
      assert this.trace == null: "This is a bug.";
      this.trace = this.implementation.make_trace();
      if (this.trace == null) {
      	throw new RuntimeException("make_trace() in estore.TraceCompQ3 should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_trace();
    }
    
    public ComponentImpl(final TraceCompQ3 implem, final TraceCompQ3.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private ITrace trace;
    
    public ITrace trace() {
      return this.trace;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private TraceCompQ3.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected TraceCompQ3.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ITrace make_trace();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected TraceCompQ3.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected TraceCompQ3.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized TraceCompQ3.Component _newComponent(final TraceCompQ3.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of TraceCompQ3 has already been used to create a component, use another one.");
    }
    this.init = true;
    TraceCompQ3.ComponentImpl  _comp = new TraceCompQ3.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public TraceCompQ3.Component newComponent() {
    return this._newComponent(new TraceCompQ3.Requires() {}, true);
  }
}
