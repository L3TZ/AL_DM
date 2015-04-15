package banking;

import banking.AccountComp;
import banking.DeskComp;
import interfaces.IAccountBasicOperation;
import interfaces.IBankTransfert;

@SuppressWarnings("all")
public abstract class BankComp {
  public interface Requires {
  }
  
  public interface Component extends BankComp.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IBankTransfert bankTransfer();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComp.Component estorePart();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComp.Component annePart();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AccountComp.Component bobPart();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DeskComp.Component deskPart();
  }
  
  public static class ComponentImpl implements BankComp.Component, BankComp.Parts {
    private final BankComp.Requires bridge;
    
    private final BankComp implementation;
    
    public void start() {
      assert this.estorePart != null: "This is a bug.";
      ((AccountComp.ComponentImpl) this.estorePart).start();
      assert this.annePart != null: "This is a bug.";
      ((AccountComp.ComponentImpl) this.annePart).start();
      assert this.bobPart != null: "This is a bug.";
      ((AccountComp.ComponentImpl) this.bobPart).start();
      assert this.deskPart != null: "This is a bug.";
      ((DeskComp.ComponentImpl) this.deskPart).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_estorePart() {
      assert this.estorePart == null: "This is a bug.";
      assert this.implem_estorePart == null: "This is a bug.";
      this.implem_estorePart = this.implementation.make_estorePart();
      if (this.implem_estorePart == null) {
      	throw new RuntimeException("make_estorePart() in banking.BankComp should not return null.");
      }
      this.estorePart = this.implem_estorePart._newComponent(new BridgeImpl_estorePart(), false);
      
    }
    
    private void init_annePart() {
      assert this.annePart == null: "This is a bug.";
      assert this.implem_annePart == null: "This is a bug.";
      this.implem_annePart = this.implementation.make_annePart();
      if (this.implem_annePart == null) {
      	throw new RuntimeException("make_annePart() in banking.BankComp should not return null.");
      }
      this.annePart = this.implem_annePart._newComponent(new BridgeImpl_annePart(), false);
      
    }
    
    private void init_bobPart() {
      assert this.bobPart == null: "This is a bug.";
      assert this.implem_bobPart == null: "This is a bug.";
      this.implem_bobPart = this.implementation.make_bobPart();
      if (this.implem_bobPart == null) {
      	throw new RuntimeException("make_bobPart() in banking.BankComp should not return null.");
      }
      this.bobPart = this.implem_bobPart._newComponent(new BridgeImpl_bobPart(), false);
      
    }
    
    private void init_deskPart() {
      assert this.deskPart == null: "This is a bug.";
      assert this.implem_deskPart == null: "This is a bug.";
      this.implem_deskPart = this.implementation.make_deskPart();
      if (this.implem_deskPart == null) {
      	throw new RuntimeException("make_deskPart() in banking.BankComp should not return null.");
      }
      this.deskPart = this.implem_deskPart._newComponent(new BridgeImpl_deskPart(), false);
      
    }
    
    protected void initParts() {
      init_estorePart();
      init_annePart();
      init_bobPart();
      init_deskPart();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final BankComp implem, final BankComp.Requires b, final boolean doInits) {
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
    
    public IBankTransfert bankTransfer() {
      return this.deskPart().deskTransfert();
    }
    
    private AccountComp.Component estorePart;
    
    private AccountComp implem_estorePart;
    
    private final class BridgeImpl_estorePart implements AccountComp.Requires {
    }
    
    public final AccountComp.Component estorePart() {
      return this.estorePart;
    }
    
    private AccountComp.Component annePart;
    
    private AccountComp implem_annePart;
    
    private final class BridgeImpl_annePart implements AccountComp.Requires {
    }
    
    public final AccountComp.Component annePart() {
      return this.annePart;
    }
    
    private AccountComp.Component bobPart;
    
    private AccountComp implem_bobPart;
    
    private final class BridgeImpl_bobPart implements AccountComp.Requires {
    }
    
    public final AccountComp.Component bobPart() {
      return this.bobPart;
    }
    
    private DeskComp.Component deskPart;
    
    private DeskComp implem_deskPart;
    
    private final class BridgeImpl_deskPart implements DeskComp.Requires {
      public final IAccountBasicOperation storeAccount() {
        return BankComp.ComponentImpl.this.estorePart().accountTransfer();
      }
      
      public final IAccountBasicOperation anneAccount() {
        return BankComp.ComponentImpl.this.annePart().accountTransfer();
      }
      
      public final IAccountBasicOperation bobAccount() {
        return BankComp.ComponentImpl.this.bobPart().accountTransfer();
      }
    }
    
    public final DeskComp.Component deskPart() {
      return this.deskPart;
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
  
  private BankComp.ComponentImpl selfComponent;
  
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
  protected BankComp.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected BankComp.Requires requires() {
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
  protected BankComp.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComp make_estorePart();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComp make_annePart();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AccountComp make_bobPart();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract DeskComp make_deskPart();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized BankComp.Component _newComponent(final BankComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of BankComp has already been used to create a component, use another one.");
    }
    this.init = true;
    BankComp.ComponentImpl  _comp = new BankComp.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public BankComp.Component newComponent() {
    return this._newComponent(new BankComp.Requires() {}, true);
  }
}
